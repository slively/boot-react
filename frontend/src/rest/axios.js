import axios from 'axios';
import { displayAuthError, getAuthToken, AUTH_TOKEN_HEADER } from 'reducers/authentication';

const badAuthCodes = [401, 403];

const setupAxiosInterceptors = onUnauthenticated => {
  const onRequestSuccess = config => {
    var token = getAuthToken();
    if (token) {
      config.headers[AUTH_TOKEN_HEADER] = token;
    }
    config.timeout = 10000;
    return config;
  };
  const onResponseSuccess = (response) => response;
  const onResponseError = error => {
    if (badAuthCodes.indexOf(error.status) >= 0) {
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
