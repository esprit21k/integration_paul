import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { reduxForm, Field } from 'redux-form';
import { connect } from 'react-redux';
import { Link } from 'react-router';

import Messages from '../notifications/Messages';
import Errors from '../notifications/Errors';

import loginRequest from './actions';

// If you were testing, you'd want to export this component
// so that you can test your custom made component and not
// test whether or not Redux and Redux Form are doing their jobs
class Login extends Component {
  // Pass the correct proptypes in for validation
  static propTypes = {
    handleSubmit: PropTypes.func,
    loginRequest: PropTypes.func,
    login: PropTypes.shape({
      requesting: PropTypes.bool,
      successful: PropTypes.bool,
      messages: PropTypes.array,
      errors: PropTypes.array,
    }),
  }

  // Remember, Redux Form passes the form values to our handler
  // In this case it will be an object with `email` and `password`
  submit = (values) => {
    this.props.loginRequest(values);
  }

  render() {
    const {
      handleSubmit, // remember, Redux Form injects this into our props
      login: {
        requesting,
        successful,
        messages,
        errors,
      },
    } = this.props;

    return (
      <div className="login">
        <div className="ui stackable three column grid">
          <div className="column">
            <form className="ui form" onSubmit={handleSubmit(this.submit)}>
              <h1>LOGIN</h1>
              <div className="field">
                <label htmlFor="username">Username</label>
                {/*
                  Our Redux Form Field components that bind email and password
                  to our Redux state's form -> login piece of state.
                */}
                <Field
                  name="username"
                  type="text"
                  id="username"
                  className="username"
                  component="input"
                />
              </div>
              <div className="field">
                <label htmlFor="password">Password</label>
                <Field
                  name="password"
                  type="password"
                  id="password"
                  className="password"
                  component="input"
                />
                <button action="submit">LOGIN</button>
              </div>
            </form>
          </div>
          <div className="column" />
          <div className="column" />
        </div>
        <div className="auth-messages">
          {/* As in the signup, we're just using the message and error helpers */}
          {!requesting && !!errors.length && (
            <Errors message="Failure to login due to:" errors={errors} />
          )}
          {!requesting && !!messages.length && (
            <Messages messages={messages} />
          )}
          {requesting && <div>Logging in...</div>}
          {!requesting && !successful && (
            <Link to="/signup">Need to Signup? Click Here »</Link>
          )}
        </div>
      </div>
    );
  }
}

// Grab only the piece of state we need
const mapStateToProps = state => ({
  login: state.login,
});

// make Redux state piece of `login` and our action `loginRequest`
// available in this.props within our component
const connected = connect(mapStateToProps, { loginRequest })(Login);

// in our Redux's state, this form will be available in 'form.login'
const formed = reduxForm({
  form: 'login',
})(connected);

// Export our well formed login component
export default formed;
