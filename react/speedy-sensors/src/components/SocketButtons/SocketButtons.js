import React from 'react';

const SocketButtons = ({handleConnect, handleSocketBoi, handleLeave}) => {
  return (
    <div>
    <button onClick={handleConnect}>Test connect</button>
    <button onClick={handleSocketBoi}>Test socketboi</button>
    <button onClick={handleLeave}>Test leave</button>
    </div>
  )
};

export default SocketButtons;
