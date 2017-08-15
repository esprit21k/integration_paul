import PropTypes from 'prop-types';
import React from 'react';
import logo from './logo.svg';
import './App.css';

const App = props => (
  <div className="App">
    <div className="App-header">
      <img src={logo} className="App-logo" alt="logo" />
      <h2>Welcome to Trumpia Integration Hub</h2>
    </div>
    <section className="App-body">
      {props.children}
    </section>
  </div>
);

App.propTypes = {
  children: PropTypes.node,
};

export default App;
