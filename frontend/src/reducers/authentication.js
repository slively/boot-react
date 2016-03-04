import { browserHistory } from 'react-router';

const LOGIN = 'authentication/LOGIN';
const LOGIN_SUCCESS = 'authentication/LOGIN_SUCCESS';
const LOGIN_FAIL = 'authentication/LOGIN_FAIL';

const LOGOUT = 'authentication/LOGOUT';

const GET_ME = 'authentication/GET_ME';
const GET_ME_SUCCESS = 'authentication/GET_ME_SUCCESS';
const GET_ME_FAIL = 'authentication/GET_ME_FAIL';

const ERROR_MESSAGE = 'authentication/ERROR_MESSAGE';

const initialState = {
  isAuthenticated: false,
  username: null,
  errorMessage: null,
  loading: true
};

// Reducer

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case LOGIN_SUCCESS:
      return {
        ...state,
        isAuthenticated: true,
        user: action.result.data,
        errorMessage: null
      };
    case LOGIN_FAIL:
      return {
        ...state,
        isAuthenticated: false,
        user: null,
        error: action.error.data
      };
    case LOGOUT:
      return {
        ...state,
        isAuthenticated: false,
        user: null
      };
    case GET_ME:
      return {
        ...state,
        loading: true
      };
    case GET_ME_SUCCESS:
      return {
        ...state,
        isAuthenticated: true,
        user: action.result.data,
        errorMessage: null,
        loading: false
      };
    case GET_ME_FAIL:
      return {
        ...state,
        isAuthenticated: false,
        user: null,
        debugError: action.error,
        loading: false
      };
    case ERROR_MESSAGE:
      return {
        ...state,
        errorMessage: action.message
      };
    default:
      return state;
  }
}

// Public action creators and async actions

const AUTH_TOKEN_KEY = 'auth-token';
export const AUTH_TOKEN_HEADER = 'authorization';

export function displayAuthError(message) {
  return {type: ERROR_MESSAGE, message};
}

export function login(username, password) {
  return {
    types: [LOGIN, LOGIN_SUCCESS, LOGIN_FAIL],
    promise: (client) => client.post('/api/auth/login', {username, password}),
    afterSuccess: (dispatch, getState, response) => {
      console.log(response.headers);
      localStorage.setItem(AUTH_TOKEN_KEY, response.headers[AUTH_TOKEN_HEADER]);
      const routingState = getState().routing.locationBeforeTransitions.state || {};
      browserHistory.push(routingState.nextPathname || '');
    }
  };
}

export function logout() {
  localStorage.removeItem(AUTH_TOKEN_KEY);
  return {
    type: LOGOUT
  };
}

export function getAuthToken() {
  return localStorage.getItem(AUTH_TOKEN_KEY);
}

export function getMe() {
  return {
    types: [GET_ME, GET_ME_SUCCESS, GET_ME_FAIL],
    promise: (client) => client.get('/api/auth/me')
  };
}

export function redirectToLoginWithMessage(messageKey) {
  return (dispatch, getState) => {
    const currentPath = getState().routing.locationBeforeTransitions.pathname;
    dispatch(displayAuthError(messageKey));
    browserHistory.replace({pathname: '/login', state: {nextPathname: currentPath}});
  }
}
