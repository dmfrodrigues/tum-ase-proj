
import '../css/page/userList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState } from "react";
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import NewOrder from '../components/NewOrder';
import { Person } from '@mui/icons-material';

function UserList() {
  const [data, setData] = useState(customerRows);

  console.log(customerRows)

  const columns = [
    {
      field: "id",
      width: 150,
      headerName: "ID"
    },
    {
      field: "name",
      width: 150,
      headerName: "Name",
    },
    {
      field: "email",
      width: 150,
      headerName: "Email",
    },
    {
      field: "type",
      width: 150,
      headerName: "Type",
    },
  ];

  return (
    <div className="userList">
      <DataGrid
        className='userListTable'
        rows={data}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
      />
    </div>
  );
}

export default UserList;