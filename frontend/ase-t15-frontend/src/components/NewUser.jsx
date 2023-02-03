import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { AddCircle, AddCircleOutline } from '@mui/icons-material';

function NewUser() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <div className="userListNew">
            <Button
                variant="outline-success"
                startIcon={<AddCircle />}
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
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter name" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Insert Email</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Insert Password</Form.Label>
                            <Form.Control type="password" placeholder="Enter password" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicDeliverer">
                            <Form.Label>Select Type</Form.Label>
                            <Form.Select aria-label="Type select" size="sm">
                                <option value="dispatcher">Dispatcher</option>
                                <option value="customer">Customer</option>
                                <option value="deliverer">Deliverer</option>
                            </Form.Select>
                        </Form.Group>
                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleClose}>
                        Create
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default NewUser;