/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";

function EditUser({ user }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <div>
            <Button variant="outline-success" size="sm" className="orderListEditBtn" onClick={handleShow}>
                Edit
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit User</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" value={user.name} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicUsername">
                            <Form.Label>Insert Username</Form.Label>
                            <Form.Control type="text" value={user.username} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Insert Email</Form.Label>
                            <Form.Control type="email" value={user.email} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicDeliverer">
                            <Form.Label>Select Type</Form.Label>
                            <Form.Select aria-label="Type select" size="sm" defaultValue={user.role}>
                                <option value="DISPATCHER" key="DISPATCHER">
                                    Dispatcher</option>
                                <option value="CUSTOMER" key="CUSTOMER">
                                    Customer</option>
                                <option value="DELIVERER" key="DELIVERER">
                                    Deliverer</option>
                            </Form.Select>
                        </Form.Group>
                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleClose}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default EditUser;