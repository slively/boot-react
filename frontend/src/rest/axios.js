import axios from 'axios';
import { displayAuthError, logout, getAuthToken } from 'reducers/authentication';

const badAuthCodes = [401, 403];

const setupAxiosInterceptors = onUnauthenticated => {
  const onRequestSuccess = config => {
    var token = getAuthToken();
    if (token) {
      config.headers['x-auth-token'] = token;
    }
    config.timeout = 10000;
    return config;
  };
  const onResponseSuccess = (response) => response;
  const onResponseError = error => {
    if (badAuthCodes.find(error.status) >= 0) {
      logout();
      onUnauthenticated();
    }
    return Promise.reject(error);
  };
  axios.interceptors.request.use(onRequestSuccess);
  axios.interceptors.response.use(onResponseSuccess, onResponseError);
};

export {
  setupAxiosInterceptors
};
