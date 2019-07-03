import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import LocationSearch from './page/LocationSearch';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<LocationSearch />, document.getElementById('location_search'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
