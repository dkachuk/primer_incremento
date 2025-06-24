import { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

export const useSGAState = () => {
  const [environments, setEnvironments] = useState([]);
  const [plantillas, setPlantillas] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8585/api/ambientes')
      .then(res => res.json())
      .then(data => {
        const adaptados = data.map(a => ({
          ...a,
          name: a.nombre ?? a.name,
          components: Array.isArray(a.cosas) ? a.cosas : []
        }));
        setEnvironments(adaptados);
      })
      .catch(err => console.error('Error al cargar ambientes', err));
  }, []);

  useEffect(() => {
    fetch('http://localhost:8585/api/plantillas')
      .then(res => res.json())
      .then(data => setPlantillas(data))
      .catch(err => console.error('Error al cargar plantillas', err));
  }, []);

  useEffect(() => {
    const socket = new SockJS('/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log('[FRONTEND] Conectado al WebSocket');

        stompClient.subscribe('/topic/actualizaciones', (mensaje) => {
          const evento = JSON.parse(mensaje.body);
          console.log('[FRONTEND] Evento WebSocket recibido:', evento);

          if (evento.evento === 'estado-reportado' && evento.cosaId !== undefined) {
            setEnvironments(prev =>
              prev.map(env => ({
                ...env,
                components: env.components.map(comp =>
                  comp.id === evento.cosaId
                    ? { ...comp, state: evento.estadoActual }
                    : comp
                )
              }))
            );
          }
        });
      }
    });

    stompClient.activate();

    return () => stompClient.deactivate();
  }, []);

  const addEnvironment = (name) => {
    const nuevo = { name };

    fetch('http://localhost:8585/api/ambientes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevo)
    })
      .then(res => res.json())
      .then(created => {
        const ambienteAdaptado = {
          ...created,
          components: Array.isArray(created.cosas) ? created.cosas : []
        };
        setEnvironments(prev => [...prev, ambienteAdaptado]);
      })
      .catch(err => console.error('Error al guardar ambiente', err));
  };

  const addComponent = (envId, type) => {
    const nuevo = {
      type,
      state: false,
      temperature: type === 'Aire' ? 24 : null
    };

    fetch(`http://localhost:8585/api/ambientes/${envId}/componentes`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevo)
    })
      .then(res => res.json())
      .then(component => {
        const compAdaptado = {
          ...component,
          atributos: Array.isArray(component.atributos) ? component.atributos : []
        };

        setEnvironments(prev =>
          prev.map(env =>
            env.id === envId
              ? { ...env, components: [...env.components, compAdaptado] }
              : env
          )
        );
      })
      .catch(err => console.error('Error al agregar componente', err));
  };

  const toggleComponent = (envId, compId) => {
    const ambienteActual = environments.find(env => env.id === envId);
    if (!ambienteActual) return;

    const componenteActual = ambienteActual.components.find(c => c.id === compId);
    if (!componenteActual) return;

    const nuevoEstado = !componenteActual.state;

    setEnvironments(prev =>
      prev.map(env =>
        env.id === envId
          ? {
              ...env,
              components: env.components.map(comp =>
                comp.id === compId ? { ...comp, state: nuevoEstado } : comp
              )
            }
          : env
      )
    );

    fetch(`http://localhost:8585/api/ambientes/${envId}/componentes/${compId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ state: nuevoEstado })
    }).catch(err => console.error('Error al cambiar estado del componente', err));
  };

  const updateTemperature = (envId, compId, nuevosAtributos) => {
    setEnvironments(prev =>
      prev.map(env =>
        env.id === envId
          ? {
              ...env,
              components: env.components.map(comp =>
                comp.id === compId ? { ...comp, atributos: nuevosAtributos } : comp
              )
            }
          : env
      )
    );
  };

  return {
    environments,
    addEnvironment,
    addComponent,
    toggleComponent,
    updateTemperature,
    plantillas
  };
};