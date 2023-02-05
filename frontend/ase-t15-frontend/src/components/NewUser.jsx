import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useDispatch, useSelector } from 'react-redux'
import { AddCircleOutline } from '@mui/icons-material';
import { createUser } from '../actions/users';
import NewToken from './NewToken';

function NewUser({ allTokens }) {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const tokens = useSelector(state => state.users.tokens);

    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [token, setToken] = useState(tokens ? tokens.length > 0 ? tokens[0].id : "" : "");
    const [role, setRole] = useState("dispatcher");

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        // console.log("Submitting new user")
        // console.log(user);
        // console.log(username);
        // console.log(email);
        // console.log(password);
        // console.log(role);
        dispatch(createUser({ name, username, email, password, role, token }));
        handleClose();
    }

    return (
        <div className="userListNew">
            <Button
                variant="outline-success"
                onClick={handleShow}
                className="userListNewButton"
            >
                <AddCircleOutline />
                <span className="userListNewButtonSpan">New User</span>
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New User</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicName" onChange={(e) => setName(e.target.value)}>
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter name" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicUsername" onChange={(e) => setUsername(e.target.value)}>
                            <Form.Label>Insert Username</Form.Label>
                            <Form.Control type="text" placeholder="Enter username" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail" onChange={(e) => setEmail(e.target.value)}>
                            <Form.Label>Insert Email</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword" onChange={(e) => setPassword(e.target.value)}>
                            <Form.Label>Insert Password</Form.Label>
                            <Form.Control type="password" placeholder="Enter password" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicToken">
                            <Form.Label>Select Token</Form.Label>
                            <div className="mb-3 d-flex flex-row">
                                <Form.Select aria-label="Token select" className='flex-fill p-2' size="sm" value={token} onChange={(e) => setToken(e.target.value)}>
                                    {
                                        tokens &&
                                        tokens.filter(token => !token.principal)
                                            .map((token) => (
                                                <option key={token.id} value={token.id}>{token.id}</option>
                                            ))}
                                </Form.Select>
                                {/* Button to add token */}
                                <NewToken />
                            </div>
                        </Form.Group>



                        <Form.Group className="mb-3" controlId="formBasicType">
                            <Form.Label>Select Type</Form.Label>
                            <Form.Select aria-label="Type select" size="sm" value="dispatcher" onChange={(e) => setRole(e.target.value)}>
                                <option key="dispatcher" value="dispatcher">Dispatcher</option>
                                <option key="customer" value="customer">Customer</option>
                                <option key="deliverer" value="deliverer">Deliverer</option>
                            </Form.Select>
                        </Form.Group>
                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleSubmit}>
                        Create
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default NewUser;