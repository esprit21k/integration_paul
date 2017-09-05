import { REGISTER_REQUESTING } from './constants';

const registerRequest = function registerRequest({ username, email, password }) {
  return {
    type: REGISTER_REQUESTING,
    username,
    email,
    password,
  };
};

export default registerRequest;
