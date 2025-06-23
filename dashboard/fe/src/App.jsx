import React, { useState } from 'react';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import KonvaCanvas from './components/KonvaCanvas';
import PlantillaPanel from './components/PlantillaPanel';
import { useSGAState } from './hooks/useSGAState';

const App = () => {
  const sga = useSGAState();
  const [mostrarPanel, setMostrarPanel] = useState(false);

  return (
    <div style={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Header />
      <div style={{ flex: 1, display: 'flex' }}>
        <Sidebar
          onAddEnvironment={sga.addEnvironment}
          onTogglePlantilla={() => setMostrarPanel(prev => !prev)}
          plantillaVisible={mostrarPanel}
        />

        <div style={{ flex: 1, padding: '24px', overflow: 'auto' }}>
          {mostrarPanel && (
            <div style={{ marginBottom: 16, background: '#e8f5e9', padding: 16, borderRadius: 8 }}>
              <PlantillaPanel />
            </div>
          )}

        <KonvaCanvas
          environments={sga.environments}
          plantillas={sga.plantillas}
          addComponent={sga.addComponent}
          toggleComponent={sga.toggleComponent}
          updateTemperature={sga.updateTemperature}
        />
        </div>
      </div>
    </div>
  );
};

export default App;