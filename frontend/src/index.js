import ReactDOM from 'react-dom';
import React from 'react';
import { Provider } from 'react-redux';
import { bindActionCreators } from 'redux';
import initStore from 'config/store';
import { setupAxiosInterceptors } from 'rest/axios';
import DevTools from 'config/devtools';
import { redirectToLoginWithMessage, logout } from 'reducers/authentication';
import { setLocale } from 'reducers/locale';
import { browserHistory } from 'react-router'
import { syncHistoryWithStore } from 'react-router-redux'
import { Router } from 'react-router';
import getRoutes from 'router/router';

const devTools = process.env.NODE_ENV === 'development' ? <DevTools /> : null;
const store = initStore();
const history = syncHistoryWithStore(browserHistory, store);
const actions = bindActionCreators({redirectToLoginWithMessage, logout}, store.dispatch);

setupAxiosInterceptors(() => {
  actions.logout();
  actions.redirectToLoginWithMessage('Please login to access this resource')
});

ReactDOM.render(
  <Provider store={store}>
    <div>
      {devTools}
      <Router history={history} routes={getRoutes(actions.logout)}/>
    </div>
  </Provider>,
  document.getElementById('root')
);
