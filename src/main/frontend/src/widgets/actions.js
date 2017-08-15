import {
  WIDGET_CREATING,
  WIDGET_CREATE_SUCCESS,
  WIDGET_CREATE_ERROR,
} from './constants';

// Create requires that we pass it our current logged in client AND widget params
// which you can view at http://widgetizer.jcolemorrison.com/explorer OR at
// localhost:3002/explorer if you're using the local API version.
export const widgetCreate = function widgetCreate(client, widget) {
  return {
    type: WIDGET_CREATING,
    client,
    widget,
  };
};

export const widgetCreateSuccess = function widgetCreateSuccess(widget) {
  return {
    type: WIDGET_CREATE_SUCCESS,
    widget,
  };
};

export const widgetCreateError = function widgetCreateError(error) {
  return {
    type: WIDGET_CREATE_ERROR,
    error,
  };
};
