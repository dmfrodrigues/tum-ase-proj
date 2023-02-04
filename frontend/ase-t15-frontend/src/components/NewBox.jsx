import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { AddCircle, AddCircleOutline } from '@mui/icons-material';

function NewBox({ customers }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter Name" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAddress">
                            <Form.Label>Insert Address</Form.Label>
                            <Form.Control type="text" placeholder="Enter address" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicCustomer">
                            <Form.Label>Select Customer</Form.Label>
                            <Form.Select aria-label="Customer select" size="sm">
                                {
                                    customers.map((customer) => {
                                        return <option key={customer.id} value={customer.id}>{customer.name}</option>
                                    })
                                }
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

export default NewBox;