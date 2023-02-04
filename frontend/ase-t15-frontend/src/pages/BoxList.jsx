
import '../css/page/boxList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState, useEffect } from "react";
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import EditUser from '../components/EditUser';
import { Box } from '@mui/material';
import NewBox from '../components/NewBox';
import EditBox from '../components/EditBox';
import { useSelector, useDispatch } from 'react-redux'
import { Inventory2Outlined } from '@mui/icons-material';
import { getCustomers } from '../actions/users';
import { getBoxes } from '../actions/boxes';

function BoxList() {
  const [data, setData] = useState(boxRows);
  const dispatch = useDispatch();
  const [customers, setCustomers] = useSelector(state => state.users.customers);
  const [boxes, setBoxes] = useSelector(state => state.boxes);

  useEffect(() => {
    dispatch(getBoxes())
    dispatch(getCustomers())
  }, [])

  const getStatus = (order) => {
    return order.events[order.events.length - 1].state;
  }

  const columns = [
    {
      field: "id",
      width: 150,
      flex: 1,
      headerName: "ID",
    },
    {
      field: "name",
      width: 150,
      flex: 1,
      headerName: "Name",
      renderCell: (params) => {
        return (
          <div className="userListItem">
            <Inventory2Outlined />
            {"  " + params.row.username}
          </div>
        );
      }
    },
    {
      field: "address",
      width: 150,
      flex: 2,
      headerName: "Address",
    },
    {
      field: "customer",
      width: 150,
      flex: 1,
      headerName: "Customer",
      valueGetter: (params) => {
        return params.row.customer.name;
      },
    },
    {
      renderHeader: () => {
        return (
          <NewBox customers={customers} />
        );
      },
      flex: 1,
      sortable: false,
      filterable: false,
      field: "action",
      renderCell: (params) => {
        return (
          <div className="orderListEdit">
            <EditBox box={params.row} customers={customers} />
            <DeleteModal text="Confirm Box Deletion" />
          </div>
        );
      },
    },
  ];

  return (
    <div className="boxList">
      <DataGrid
        className='boxListTable'
        rows={boxes}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
      />
    </div>
  );
}

export default BoxList;