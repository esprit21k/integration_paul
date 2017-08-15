import { SIGNUP_REQUESTING } from './constants';

const signupRequest = function signupRequest({ username, email, password }) {
  return {
    type: SIGNUP_REQUESTING,
    username,
    email,
    password,
  };
};

export default signupRequest;
