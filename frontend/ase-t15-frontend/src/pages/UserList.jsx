
import '../css/page/userList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState } from "react";
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import { Person } from '@mui/icons-material';
import NewUser from '../components/NewUser';
import EditUser from '../components/EditUser';

function UserList() {
  const [data, setData] = useState(customerRows);

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
            <Person />
            {"  " + params.row.name}
          </div>
        );
      }
    },
    {
      field: "email",
      width: 150,
      flex: 1,
      headerName: "Email",
    },
    {
      field: "type",
      width: 150,
      flex: 1,
      headerName: "Type",
      renderCell: (params) => {
        return (
          <span className="userType">
            {params.row.type[0].toUpperCase() + params.row.type.slice(1)}
          </span>
        );
      }
    },
    {
      renderHeader: () => {
        return (
          <NewUser />
        );
      },
      flex: 1,
      sortable: false,
      filterable: false,
      field: "action",
      renderCell: (params) => {
        return (
          <div className="userListEdit">
            <EditUser user={params.row} />
            <DeleteModal text="Confirm User Deletion" />
          </div>
        );
      },
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