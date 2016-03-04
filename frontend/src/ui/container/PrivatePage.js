import React, {Component} from 'react';
import {connect} from 'react-redux';
import Translate from 'react-translate-component';

export class PrivatePage extends Component {

  render() {
    return (
      <div>
        <h2>Private page</h2>
        <p>"Hello {this.props.user.username}! How are you today?"</p>
      </div>
    )
  }
}

export default connect(
  ({authentication}) => ({user: authentication.user})
)(PrivatePage);
