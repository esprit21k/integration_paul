import { Container } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import React from 'react';
import './App.css';

const App = props => (
  <div className="App">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.js" />
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" />

    <div className="App-header">
      <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Trumpia_Logo_2015.png/800px-Trumpia_Logo_2015.png" className="App-logo" alt="logo" />
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
