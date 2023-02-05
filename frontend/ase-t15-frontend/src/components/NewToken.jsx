import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useDispatch } from 'react-redux'
import { AddCircleOutline } from '@mui/icons-material';
import { createToken } from '../actions/users';

function NewToken() {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);

    const [id, setId] = useState("");

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        // console.log("Submitting new token")
        // console.log(id);
        dispatch(createToken({ id }));
        handleClose();
    }

    return (
        <div className="userListNew">
            <Button variant="outline-success" size="sm" className='addToken' onClick={handleShow}>
                <AddCircleOutline />
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Token</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicToken" onChange={(e) => setId(e.target.value)}>
                            <Form.Label>Insert Token</Form.Label>
                            <Form.Control type="text" placeholder="Enter token" />
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

export default NewToken;