import React, {useState} from "react";
import {withRouter} from "react-router-dom";
import {DropdownButton, Dropdown} from "react-bootstrap";

function Game(){
    return(
        <div className="col-12 bg-white">
            <div>
                <div></div>
                <div>
                </div>
            </div>
            <div>
                <div id="panel">
                    <div>
                        <DropdownButton title="New game" id="dropdown-basic-button">
                            <Dropdown.Item href="/game">Easy</Dropdown.Item>
                            <Dropdown.Item href="/game">Medium</Dropdown.Item>
                            <Dropdown.Item href="/game">Hard</Dropdown.Item>
                            <Dropdown.Item href="/game">Test</Dropdown.Item>
                        </DropdownButton>

                    </div>
                    <div></div>
                    <div id="hintsPanel">
                        <div id="hints">
                            <h3>Number of hints:</h3>
                        </div>
                    </div>
                </div>
                <div></div>
                <span></span>
            </div>
            <div>
                <div></div>
                <div></div>
            </div>
        </div>
    )
}

export default withRouter(Game);