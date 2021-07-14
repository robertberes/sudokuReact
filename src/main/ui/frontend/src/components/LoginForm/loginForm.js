import React, {useState} from 'react';
import {API_BASE_URL, ACCESS_TOKEN_NAME} from '../../constants/apiConstants';
import { withRouter } from "react-router-dom";
import axios from "axios";

function LoginForm(props) {
    const [state , setState] = useState({
        username : "",
        password : "",
        successMessage: null
    })
    const handleChange = (e) => {
        const {id , value} = e.target
        setState(prevState => ({
            ...prevState,
            [id] : value
        }))
    }

     const handleSubmitClick = (e) => {
        e.preventDefault();
        const payload={
            username: state.username,
            password: state.password,
        }
        axios.get('http://localhost:8080/api/users/' + payload.username)
            .then(function (response){
                if (response.status === 200 && payload.password === response.data){
                    setState(prevState => ({
                        ...prevState,
                        'successMessage' : 'Login successful. Redirecting to game...'
                    }))
                    localStorage.setItem(ACCESS_TOKEN_NAME, response.data.token);
                    redirectToGame();
                    props.showError(null);
                }
                else if(response.status === 204 || payload.password !== response.data){
                    props.showError("Username and password do not match");
                }
                else{
                    props.showError("Username does not exists");
                }
            })
            .catch(function (error) {
                props.showError("Username does not exists");
                console.log(error);
            });
     }
    const redirectToHome = () => {
        props.updateTitle('Home');
        props.history.push('/home');
    }
    const redirectToGame = () => {
        props.updateTitle('Game');
        props.history.push('/game');
    }
    const redirectToRegister = () => {
        props.history.push('/register');
        props.updateTitle('Register');
    }
    return(
        <div className="card col-12 col-lg-4 login-card mt-2 hv-center">
            <form>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputUserName1">Username</label>
                    <input type="text"
                           className="form-control"
                           id="username"
                           aria-describedby="emailHelp"
                           placeholder="Enter Username"
                           value={state.username}
                           onChange={handleChange}
                    />
                </div>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword1">Password</label>
                    <input type="password"
                           className="form-control"
                           id="password"
                           placeholder="Password"
                           value={state.password}
                           onChange={handleChange}
                    />
                </div>
                <div className="form-check">
                </div>
                <button
                    type="submit"
                    className="btn btn-primary"
                    onClick={handleSubmitClick}
                >Submit</button>
            </form>
            <div className="alert alert-success mt-2" style={{display: state.successMessage ? 'block' : 'none' }} role="alert">
                {state.successMessage}
            </div>
            <div className="registerMessage">
                <span>Dont have an account? </span>
                <span className="loginText" onClick={() => redirectToRegister()}>Register</span>
            </div>
        </div>
    )
}

export default withRouter(LoginForm);