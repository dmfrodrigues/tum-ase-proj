import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useDispatch } from "react-redux";
import { AddCircleOutline } from '@mui/icons-material';
import { createBox } from '../actions/boxes';

function NewBox() {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [address, setAddress] = useState("");

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        console.log("Submitting new box")
        console.log(username);
        console.log(password);
        console.log(address);
        dispatch(createBox({ username, password, address }));
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
                <span className="userListNewButtonSpan">New Box</span>
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Box</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicName" onChange={(e) => setUsername(e.target.value)}>
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter Name" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword" onChange={(e) => setPassword(e.target.value)}>
                            <Form.Label>Insert Password</Form.Label>
                            <Form.Control type="password" placeholder="Enter Password" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAddress" onChange={(e) => setAddress(e.target.value)}>
                            <Form.Label>Insert Address</Form.Label>
                            <Form.Control type="text" placeholder="Enter address" />
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

export default NewBox;