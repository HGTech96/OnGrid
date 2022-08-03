import React from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css';
import LogIn from './pages/Main/LogIn';
import SignIn from './pages/Main/SignIn';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LogIn />} />
        <Route path="/register" element={<SignIn />} />
      </Routes>
    </Router>
  );
}

export default App;
