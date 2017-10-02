import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { reduxForm, Field } from 'redux-form';
import { connect } from 'react-redux';
import { Link } from 'react-router';

// Import the helpers.. that we'll make here in the next step
import Messages from '../notifications/Messages';
import Errors from '../notifications/Errors';

import signupRequest from './actions';

class Signup extends Component {
  // Pass the correct proptypes in for validation
  static propTypes = {
    handleSubmit: PropTypes.func,
    signupRequest: PropTypes.func,
    signup: PropTypes.shape({
      requesting: PropTypes.bool,
      successful: PropTypes.bool,
      messages: PropTypes.array,
      errors: PropTypes.array,
    }),
  }

  submit = (values) => {
    this.props.signupRequest(values);
  }

  render() {
    const {
      handleSubmit,
      signup: {
        requesting,
        successful,
        messages,
        errors,
      },
    } = this.props;

    return (
      <div className="signup">
        <div className="ui stackable three column grid">
          <div className="column">
            <form
              className="ui form"
              onSubmit={handleSubmit(this.submit)}
            >
              <h1>Signup</h1>
              <div className="field">
                <label htmlFor="username">Username</label>
                <Field
                  name="username"
                  type="text"
                  component="input"
                />
              </div>

              <div className="field">
                <label htmlFor="email">Email</label>
                <Field
                  name="email"
                  type="text"
                  component="input"
                />
              </div>
              <div className="field">
                <label htmlFor="password">Password</label>
                <Field
                  name="password"
                  type="password"
                  component="input"
                />
              </div>
              <button action="submit">SIGNUP</button>
            </form>
          </div>
        </div>
        <div className="auth-messages">
          {
          }
          {!requesting && !!errors.length && (
            <Errors
              message="Failure to signup due to:"
              errors={errors}
            />
          )}
          {!requesting && !!messages.length && (
            <Messages messages={messages} />
          )}
          {!requesting && successful && (
            <div>
              Signup Successful! <Link to="/login">Click here to Login »</Link>
          </div>
        )}
        {/* Redux Router's <Link> component for quick navigation of routes */}
        {!requesting && !successful && (
          <Link to="/login">
            Already a Widgeter? Login Here »
          </Link>
        )}
      </div>
    </div>
    );
  }
}

const mapStateToProps = state => ({
  signup: state.signup,
});

const connected = connect(mapStateToProps, { signupRequest })(Signup);

const formed = reduxForm({
  form: 'signup',
})(connected);

// Export our well formed component!
export default formed;
