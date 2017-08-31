import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { signinRedirect, signout } from './actions';


class Topbar extends Component {

  static propTypes = {
    client: PropTypes.shape({
      token: PropTypes.string.isRequired,
    }),
    signinRedirect: PropTypes.func,
    signout: PropTypes.func,
  }


  componentDidMount = () => {
    $('.ui.dropdown').dropdown();
  }

  logout = () => {
    this.props.signout();
  }

  submit = () => {
    this.props.signinRedirect();
  }

  render() {
    const loggedIn = this.props.client.token !== '' && this.props.client.token !== null;
    return (
      <div className="ui small menu">
        <a className="active item" href="/#/dashboard">
          Home
        </a>
        <div className="ui dropdown item" href="">
          Accounts <i className="dropdown icon" />
          <div className="menu">
            <a className="item" href="/#/dashboard/trumpia">Trumpia</a>
          </div>
        </div>
        <div className="right menu">
          <div className="item">
            {loggedIn ? (
              <div className="ui primary button" role="presentation" onClick={this.logout}>Log out</div>
            ) : (
              <div className="ui primary button" role="presentation" onClick={this.submit}>Sign In</div>
            )}
          </div>
        </div>
      </div>
    );
  }
}

// Grab only the piece of state we need
const mapStateToProps = state => ({
  client: state.client,
});

// make Redux state piece of `login` and our action `loginRequest`
// available in this.props within our component
const connected = connect(mapStateToProps, { signinRedirect, signout })(Topbar);


export default connected;
