import React, {useState} from "react";
import {API_BASE_URL, ACCESS_TOKEN_NAME} from '../../constants/apiConstants';
import {withRouter} from "react-router-dom";
import axios from "axios";


function RegistrationForm(props) {
    const [state, setState] = useState({
        userName: "",
        password: "",
        confirmPassword: ""
    })
    const handleChange = (e) => {
        const {id, value} = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }



    const sendDetailsToServer = () => {

        if ((state.userName.length) && (state.password.length)) {
            props.showError(null);
            const payload = {
                username: state.userName,
                password: state.password,
                registered_on: Date.now(),
                show_tips: true,
            };
            axios({
                method: 'POST',
                url: 'http://localhost:8080/api/users',
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                },
                data: payload
            })
                .then(function (response) {
                    if(response.status === 200){
                        setState(prevState => ({
                            ...prevState,
                            'successMessage' : 'Registration successful. Redirecting to home page..'
                        }))
                        localStorage.setItem(ACCESS_TOKEN_NAME,response.data.token);
                        redirectToLogin();
                        props.showError(null)
                    } else{
                        props.showError("Some error ocurred");
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        } else {
            props.showError('Please enter valid username and password');
        }
    }
    const redirectToLogin = () => {
        props.updateTitle('Login')
        props.history.push('/login');
    }
    const handleSubmitClick = (e) => {
        e.preventDefault();
        if (state.password === state.confirmPassword) {
            sendDetailsToServer()
        } else {
            props.showError('Passwords do not match');
        }
    }

    return (
        <div className="card col-12 col-lg-4 login-card mt-2 hv-center">

            <form>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputUsername1">User name</label>
                    <input type="text"
                           className="form-control"
                           id="userName"
                           placeholder="Enter username"
                        // value={state.username}
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
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword1">Confirm Password</label>
                    <input type="password"
                           className="form-control"
                           id="confirmPassword"
                           placeholder="Confirm Password"
                           value={state.confirmPassword}
                           onChange={handleChange}
                    />
                </div>
                <button type="submit" className="btn btn-primary" onClick={handleSubmitClick}>
                    Register
                </button>
            </form>
            <div className="registerMessage">
                <span>Already have an account? </span>
                <span className="loginText" onClick={() => redirectToLogin()}>Log In</span>
            </div>
        </div>
    )
}

export default withRouter(RegistrationForm);