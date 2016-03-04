import React, { Component } from 'react';
import { Form, TextInput, Label } from 'react-easy-form';

const LabeledInput = (props) => (
  <div className="pure-control-group">
    <Label value={props.label} position="before">
      <TextInput {...props}/>
    </Label>
  </div>
);

const ErrorPanel = ({messageKey}) => (
  <p className="error-panel">{messageKey}</p>
);

export default class LoginForm extends Component {

  render() {
    const {errorMessage} = this.props;
    const errorPanel = errorMessage ? <ErrorPanel messageKey={errorMessage}/> : null;
    return (
      <div>
        <h2 content="Login Page" />

        {errorPanel}

        <Form ref="form" initialData={{}} onSubmit={(formData) => this.handleSubmit(formData)} className="pure-form pure-form-aligned">
          <LabeledInput label="Login" name="username"/>
          <LabeledInput label="Password" name="password" type="password"/>

          <div className="pure-controls">
            <button type="submit" className="pure-button pure-button-primary">Login</button>
          </div>
        </Form>
      </div>
    );
  }

  handleSubmit(formData) {
    const { username, password } = formData;
    const { login } = this.props;
    login(username, password);
  }
}
