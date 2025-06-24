// src/components/PlantillaPanel.jsx
import React, { useState, useEffect } from 'react';

const PlantillaPanel = () => {
  const [nombre, setNombre] = useState('');
  const [atributos, setAtributos] = useState([]);
  const [plantillasExistentes, setPlantillasExistentes] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8585/api/plantillas')
      .then(res => res.json())
      .then(data => {
        if (Array.isArray(data)) {
          setPlantillasExistentes(data);
        } else if (Array.isArray(data.plantillas)) {
          setPlantillasExistentes(data.plantillas);
        } else {
          console.error('Respuesta inesperada:', data);
          setPlantillasExistentes([]);
        }
      })
      .catch(err => console.error('Error al obtener plantillas', err));
  }, []);

  const agregarAtributo = () => {
    setAtributos([...atributos, { nombre: '', valorPorDefecto: '' }]);
  };

  const actualizarAtributo = (i, campo, valor) => {
    const copia = [...atributos];
    copia[i][campo] = valor;
    setAtributos(copia);
  };

  const eliminarAtributo = (i) => {
    const copia = [...atributos];
    copia.splice(i, 1);
    setAtributos(copia);
  };

  const crearPlantilla = () => {
    const nueva = {
      nombre,
      atributos
    };

    fetch('http://localhost:8585/api/plantillas', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nueva)
    })
      .then(res => res.json())
      .then(data => {
        alert('Plantilla creada correctamente');
        setNombre('');
        setAtributos([]);
        setPlantillasExistentes(prev => [...prev, data]);
      })
      .catch(err => console.error('Error al crear plantilla', err));
  };

  return (
    <div style={{ padding: 16, background: '#e8f5e9' }}>
      <h3>Crear nueva plantilla de Cosa</h3>
      <input
        type="text"
        placeholder="Nombre (Ej: Aire)"
        value={nombre}
        onChange={e => setNombre(e.target.value)}
      />
      <h4>Atributos</h4>
      {atributos.map((attr, i) => (
        <div key={i}>
          <input
            placeholder="Nombre"
            value={attr.nombre}
            onChange={e => actualizarAtributo(i, 'nombre', e.target.value)}
          />
          <input
            placeholder="Valor por defecto"
            value={attr.valorPorDefecto}
            onChange={e => actualizarAtributo(i, 'valorPorDefecto', e.target.value)}
          />
          <button onClick={() => eliminarAtributo(i)}>❌</button>
        </div>
      ))}
      <button onClick={agregarAtributo}>➕ Agregar Atributo</button>
      <button onClick={crearPlantilla} style={{ marginLeft: 8 }}>💾 Crear Plantilla</button>

      <hr />
      <h4>Plantillas existentes</h4>
      <ul>
        {Array.isArray(plantillasExistentes) && plantillasExistentes.length > 0 ? (
          plantillasExistentes.map(p => (
            <li key={p.id}>{p.nombre}</li>
          ))
        ) : (
          <li>No hay plantillas disponibles</li>
        )}
      </ul>
    </div>
  );
};

export default PlantillaPanel;
