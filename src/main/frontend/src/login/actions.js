import {
  LOGIN_REQUESTING,
} from './constants';

const loginRequest = function loginRequest({ username, password }) {
  return {
    type: LOGIN_REQUESTING,
    username,
    password,
  };
};

export default loginRequest;
