/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useDispatch } from "react-redux";
import { editBox } from '../actions/boxes';

function EditBox({ box }) {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const [username, setUsername] = useState(box.username);
    const [password, setPassword] = useState("");
    const [address, setAddress] = useState(box.address);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        console.log("Submitting new box")
        dispatch(editBox({ id: box.id, username, password, address }));
        handleClose();
    }

    return (
        <div>
            <Button variant="outline-success" size="sm" className="boxListEditBtn" onClick={handleShow}>
                Edit
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit Box</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicName" onChange={(e) => setUsername(e.target.value)}>
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" value={username} placeholder="Enter name" onChange={(e) => setUsername(e.target.value)} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword" onChange={(e) => setPassword(e.target.value)}>
                            <Form.Label>Insert Password</Form.Label>
                            <Form.Control type="password" placeholder="Enter Password" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAddress" onChange={(e) => setAddress(e.target.value)}>
                            <Form.Label>Insert Address</Form.Label>
                            <Form.Control type="text" value={address} placeholder="Enter address" onChange={(e) => setAddress(e.target.value)} />
                        </Form.Group>
                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleSubmit}>
                        Edit
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default EditBox;