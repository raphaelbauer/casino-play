import React, { useState } from 'react';

const SlotGame = () => {
  const [slots, setSlots] = useState([0, 0, 0]);
  const [result, setResult] = useState('');

  const spin = () => {
    const newSlots = slots.map(() => Math.floor(Math.random() * 3));
    setSlots(newSlots);
    if (newSlots.every((val, i, arr) => val === arr[0])) {
      setResult('You win!');
    } else {
      setResult('Try again.');
    }
  };

  return (
    <div>
      <div>{slots.join(' | ')}</div>
      <button onClick={spin}>Spin</button>
      <div>{result}</div>
    </div>
  );
};

export default SlotGame;
