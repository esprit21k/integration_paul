import PropTypes from 'prop-types';
import React from 'react';
import Topbar from './topbar';
import './App.css';


const App = props => (
  <div>
    <Topbar />
    <section className="App-body">
      {props.children}
    </section>
  </div>
);

App.propTypes = {
  children: PropTypes.node,
};

export default App;
