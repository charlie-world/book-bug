import React, { Component } from 'react';
import Config from './config.js';
import { Link, Redirect } from 'react-router-dom';
import 'whatwg-fetch';
import logo from './logo.png';
import Join from './Join.js';

class Login extends Component {
	constructor(){
		super(...arguments);
		this.state = {
			requestID:'',
			requestPW:''
		};
	
		this.requestIDChange = this.requestIDChange.bind(this);
		this.requestPWChange = this.requestPWChange.bind(this);
	}

	onJoin() {
		this.props.history.push('/join');
	}

	onSubmit() {
		let userInfo = {
			'id':this.state.requestID,
			'password':this.state.requestPW
		};
		let apiPath = `${Config.host}/api/v1/users/login`;
		fetch(apiPath, {
			method: 'POST',
			headers:{
				'Content-Type': 'application/json',
				'Access-Control-Allow-Origin': '*'
			},
			body: JSON.stringify(userInfo)
	    }).then((response)=> response.json())
		.then((responseData)=> {
			if (responseData.meta.result_code === 200) {
				CacheStorage.setItem('token', responseData.data.token);
				this.props.history.push('/search');
			} else {
				alert(responseData.meta.result_msg);
			}
		});
	}

	requestIDChange(event) {
		this.setState({requestID: event.target.value});
	}
	requestPWChange(event) {
		this.setState({requestPW: event.target.value});
	}

	render() {
		if (CacheStorage.getItem('token') != null) {
			return (
					<Redirect to="/search"/>
			);
		} else {
			return (
				<div className="loginpanel">
					<div className="loginwindow">
					<img src={logo} alt="logo"/>
						<ul>
							<h2 className="title">Login</h2>
							<h3><input type="text" name="requestID" placeholder="Id" value={this.state.requestID} onChange={this.requestIDChange}/></h3>
							<h3><input type="password" name="requestPW" placeholder="Password" value={this.state.requestPW} onChange={this.requestPWChange}/></h3>
							<h3><button className="loginwindowbutton" onClick={this.onSubmit.bind(this)}>로그인</button></h3>
							<h3><button className="joinwindowbutton" onClick={this.onJoin.bind(this)}>회원가입</button></h3>
						</ul>
					</div>
				</div>
			);
		}
	}
}

export default Login;