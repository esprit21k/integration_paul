import PropTypes from 'prop-types';
import React from 'react';

const Sidebar = props => (
  <div className="ui sidebar vertical left menu overlay visible sidebar1">
    <div className="item logo">
      <img alt="logo" src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Trumpia_Logo_2015.png/800px-Trumpia_Logo_2015.png" />
    </div>
    <div className="ui accordion">
      <a href="/#/dashboard" className="title item">Dashboard<i className="dropdown icon" />
      </a>
      <div className="content">
        <a className="item" href="dashboard.html">Dashboard
        </a>
      </div>

      <div className="ui dropdown item displaynone">
        <z>Dashboard</z>
        <i className="icon demo-icon heart icon-heart" />

        <div className="menu">
          <div className="header">
            Dashboard
          </div>
          <div className="ui divider" />
          <a className="item" href="dashboard.html">Dashboard
                        </a>
        </div>
      </div>
    </div>

  </div>
);

Sidebar.propTypes = {
  children: PropTypes.node,
};

export default Sidebar;
