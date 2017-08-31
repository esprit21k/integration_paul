import {
  SIGNIN_REDIRECT,
  SIGNOUT,
} from './constants';

export const signinRedirect = function signinRedirect() {
  return {
    type: SIGNIN_REDIRECT
  };
};

export const signout = function signoutRedirect() {
  return {
    type: SIGNOUT
  };
};
