import {
  LOGIN_REQUESTING,
  LOGIN_SUCCESS,
  LOGIN_ERROR,
  LOGOUT_REQUESTING,
  LOGOUT_SUCCESS
} from './constants';

const initialState = {
  requesting: false,
  succesful: false,
  messages: [],
  errors: [],
};

const reducer = function loginReducer(state = initialState, action) {
  switch (action.type) {
    // Set the requesting flag and append a message to be shown
    case LOGIN_REQUESTING:
      return {
        requesting: true,
        successful: false,
        messages: [{ body: 'Logging in...', time: new Date() }],
        errors: [],
      };

    // Successful?  Reset the login state.
    case LOGIN_SUCCESS:
      return {
        errors: [],
        messages: [],
        requesting: false,
        successful: true,
      };

    case LOGOUT_REQUESTING:
      return {
        requesting: true,
        successful: false,
        messages: [{ body: 'Logging out...', time: new Date() }],
        errors: [],
      };

    case LOGOUT_SUCCESS:
      return {
        errors: [],
        messages: [{ body: 'You have been logged out.', time: new Date() }],
        requesting: false,
        successful: true,
      };

    // Append the error returned from our api
    // set the success and requesting flags to false
    case LOGIN_ERROR:
      return {
        errors: state.errors.concat([{
          body: action.error,
          time: new Date(),
        }]),
        messages: [],
        requesting: false,
        successful: false,
      };

    default:
      return state;
  }
};

export default reducer;
