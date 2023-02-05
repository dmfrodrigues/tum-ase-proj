import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useDispatch } from 'react-redux'
import { AddCircleOutline } from '@mui/icons-material';
import { createUser } from '../actions/users';

function NewUser() {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);

    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
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
        dispatch(createUser({ name, username, email, password, role }));
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