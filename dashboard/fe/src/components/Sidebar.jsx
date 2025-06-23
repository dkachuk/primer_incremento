import React from 'react';

const Sidebar = ({ onAddEnvironment, onTogglePlantilla, plantillaVisible }) => {
  const handleAdd = () => {
    const name = prompt('Nombre del nuevo ambiente:');
    if (name?.trim()) {
      onAddEnvironment(name.trim());
    }
  };

  return (
    <div style={{ width: 200, padding: 16, background: '#f0f0f0', overflowY: 'auto' }}>
      <h3>Menú</h3>

      <button onClick={onTogglePlantilla} style={{ marginBottom: 8 }}>
        {plantillaVisible ? '⬅ Ocultar Plantillas' : '📋 Administrar Plantillas'}
      </button>

      <button onClick={handleAdd}>
        ➕ Agregar Ambiente
      </button>
    </div>
  );
};

export default Sidebar;




/*import React from 'react';

const Sidebar = ({ onAddEnvironment }) => {
  const handleAdd = () => {
    const name = prompt('Nombre del nuevo ambiente:');
    if (name?.trim()) {
      onAddEnvironment(name.trim());
    }
  };

  return (
    <div style={{ width: 200, padding: 16, background: '#f0f0f0' }}>
      <h3>Menú</h3>
      <button onClick={handleAdd} style={{ marginTop: 8 }}>Agregar Ambiente</button>
    </div>
  );
};

export default Sidebar;*/