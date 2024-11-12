import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import SlotGame from './components/SlotGame';
import AdminPanel from './components/AdminPanel';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} />
          <Route path="/dashboard" component={Dashboard} />
          <Route path="/slot-game" component={SlotGame} />
          <Route path="/admin" component={AdminPanel} />
        </Switch>
      </Router>
    </AuthProvider>
  );
};

export default App;
