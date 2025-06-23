import React, { useState } from 'react';
import { Stage, Layer, Rect, Text, Group, Circle } from 'react-konva';

const KonvaCanvas = ({ environments, plantillas, addComponent, toggleComponent, updateTemperature }) => {
  const [contextMenu, setContextMenu] = useState({ visible: false, x: 0, y: 0, envId: null });

  const handleRightClick = (e, envId) => {
    e.evt.preventDefault();
    setContextMenu({ visible: true, x: e.evt.clientX, y: e.evt.clientY, envId });
  };

  const handleMenuSelect = (type) => {
    if (contextMenu.envId) {
      addComponent(contextMenu.envId, type);
    }
    setContextMenu({ visible: false, x: 0, y: 0, envId: null });
  };

  const handleDoubleClick = (envId, comp) => {
    if (!comp.atributos || comp.atributos.length === 0) {
      alert('Este componente no tiene atributos editables.');
      return;
    }

    const nuevosAtributos = comp.atributos.map(attr => {
      const nuevoValor = prompt(`Nuevo valor para ${attr.nombre}:`, attr.valor);
      return nuevoValor !== null ? { ...attr, valor: nuevoValor } : attr;
    });

    fetch(`http://190.0.100.21:8585/api/ambientes/${envId}/componentes/${comp.id}/dinamico`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ atributos: nuevosAtributos })
    })
      .then(() => {
        updateTemperature(envId, comp.id, nuevosAtributos);
      })
      .catch(err => console.error('Error al actualizar atributos dinámicos', err));
  };

  // 🔧 MODIFICACIÓN: esta función determina el color según el estado del componente
  const getComponentColor = (comp) => (comp.state ? '#4caf50' : '#f44336');

  return (
    <>
      <Stage width={window.innerWidth - 200} height={window.innerHeight - 50}>
        <Layer>
          {environments.map((env, i) => (
            <Group
              key={env.id}
              x={220 + i * 260}
              y={100}
              onContextMenu={(e) => handleRightClick(e, env.id)}
            >
              <Rect
                width={220}
                height={180}
                fillLinearGradientStartPoint={{ x: 0, y: 0 }}
                fillLinearGradientEndPoint={{ x: 0, y: 180 }}
                fillLinearGradientColorStops={[0, '#e0e0e0', 1, '#bdbdbd']}
                stroke="black"
                strokeWidth={2}
                shadowColor="rgba(0,0,0,0.4)"
                shadowBlur={10}
                shadowOffset={{ x: 5, y: 5 }}
                shadowOpacity={0.4}
                cornerRadius={8}
              />
              <Text text={env.name} x={12} y={12} fontSize={16} fontStyle="bold" />

              {(env.components || []).map((comp, j) => {
                const y = 60 + j * 60;

                return (
                  <Group
                    key={comp.id}
                    x={20}
                    y={y}
                    onClick={() => toggleComponent(env.id, comp.id)}
                    onDblClick={() => handleDoubleClick(env.id, comp)}
                  >
                    {comp.type === 'Aire' ? (
                      <Circle radius={12} fill={getComponentColor(comp)} />
                    ) : (
                      <Rect width={24} height={24} fill={getComponentColor(comp)} cornerRadius={4} />
                    )}

                    <Text
                      text={comp.type}
                      x={32}
                      y={-6}
                      fontSize={14}
                      fill="#333"
                      fontStyle="bold"
                    />

                    {comp.atributos && comp.atributos.length > 0 && (
                      <Rect
                        x={30}
                        y={8}
                        width={160}
                        height={comp.atributos.length * 18 + 8}
                        fill="#ffffffcc"
                        cornerRadius={4}
                        shadowBlur={2}
                      />
                    )}

                    {(comp.atributos || []).map((attr, idx) => (
                      <Text
                        key={idx}
                        text={`${attr.nombre}: ${attr.valor}`}
                        x={36}
                        y={12 + idx * 18}
                        fontSize={12}
                        fill="#444"
                      />
                    ))}
                  </Group>
                );
              })}
            </Group>
          ))}
        </Layer>
      </Stage>

      {contextMenu.visible && (
        <div
          style={{
            position: 'absolute',
            top: contextMenu.y,
            left: contextMenu.x,
            backgroundColor: '#fff',
            border: '1px solid #ccc',
            boxShadow: '0 2px 5px rgba(0,0,0,0.2)',
            zIndex: 9999,
          }}
        >
          {plantillas.length > 0 ? (
            plantillas.map((p) => (
              <div
                key={p.id}
                style={{ padding: '8px', cursor: 'pointer' }}
                onClick={() => handleMenuSelect(p.nombre)}
              >
                ➕ Agregar {p.nombre}
              </div>
            ))
          ) : (
            <>
              <div style={{ padding: '8px', color: '#999' }}>
                ⚠ No hay plantillas disponibles
              </div>
              <div
                style={{ padding: '8px', cursor: 'pointer', color: '#0077cc' }}
                onClick={() => window.location.href = '/plantillas'}
              >
                Crear Plantilla de Cosa
              </div>
            </>
          )}
        </div>
      )}
    </>
  );
};

export default KonvaCanvas;