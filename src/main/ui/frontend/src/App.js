import React, {useState} from 'react';
import './App.css';
import Header from './components/Header/header';
import LoginForm from './components/LoginForm/loginForm';
import RegistrationForm from './components/RegistrationForm/registrationForm';
import Game from "./components/Game/game";
import PrivateRoute from './utils/PrivateRoute';
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";

import AlertComponent from './components/AlertComponent/alertComponent';
function App() {
    const [title, updateTitle] = useState(null);
    const [errorMessage, updateErrorMessage] = useState(null);
    return (
        <Router>
            <div className="App">
                <Header title={title}/>
                <div className="container d-flex align-items-center flex-column">
                    <Switch>
                        <Route path="/" exact={true}>
                            <RegistrationForm showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                        <Route path="/register">
                            <RegistrationForm showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                        <Route path="/login">
                            <LoginForm showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                        <Route path="/game">
                            <Game showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                    </Switch>
                    <AlertComponent errorMessage={errorMessage} hideError={updateErrorMessage}/>
                </div>
            </div>
        </Router>
    );
}

export default App;