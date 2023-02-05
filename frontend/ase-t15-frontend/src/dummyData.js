export const userData = [
  {
    name: "Jan",
    "pending User": 4000,
  },
  {
    name: "Feb",
    "pending User": 3000,
  },
  {
    name: "Mar",
    "pending User": 5000,
  },
  {
    name: "Apr",
    "pending User": 4000,
  },
  {
    name: "May",
    "pending User": 3000,
  },
  {
    name: "Jun",
    "pending User": 2000,
  },
  {
    name: "Jul",
    "pending User": 4000,
  },
  {
    name: "Agu",
    "pending User": 3000,
  },
  {
    name: "Sep",
    "pending User": 4000,
  },
  {
    name: "Oct",
    "pending User": 1000,
  },
  {
    name: "Nov",
    "pending User": 4000,
  },
  {
    name: "Dec",
    "pending User": 3000,
  },
];

export const orderData = [
  {
    name: "Jan",
    "Sales": 4000,
  },
  {
    name: "Feb",
    "Sales": 3000,
  },
  {
    name: "Mar",
    "Sales": 5000,
  },
];

export const customerRows = [
  {
    id: 1,
    name: "Federico Kereki",
    type: "dispatcher",
    email: "federico@gmail.com",
  },
  {
    id: 2,
    name: "Chinda Great",
    type: "dispatcher",
    email: "chinda@gmail.com",
  },
  {
    id: 3,
    name: "Ejiro ThankGod",
    type: "deliverer",
    email: "ejiro@gmail.com",
  },
  {
    id: 4,
    name: "Okoro Isaac",
    type: "deliverer",
    email: "isaac@gmail.com",
  },
  {
    id: 9,
    name: "Banigo Kene",
    type: "deliverer",
    email: "kene@gmail.com",
  },
  {
    id: 10,
    name: "Ikechi Fortune",
    type: "customer",
    email: "fortune@gmail.com",
  },
];

export const delivererRows = [
  {
    id: 1,
    username: "Samson Bolanle",
    email: "bolanle@gmail.com",
  },
  {
    id: 2,
    username: "Ugochi Success",
    email: "ugochi@gmail.com",
  },
  {
    id: 3,
    username: "Thompson Tasha",
    email: "tasha@gmail.com",
  },
  {
    id: 4,
    username: "Saviour Blessing",
    email: "blessing@gmail.com",
  },
];

export const dispatcherRows = [
  {
    id: 1,
    username: "Saviour Blessing",
    email: "blessing@gmail.com",
  },
  {
    id: 2,
    username: "Thompson Tasha",
    email: "test@gmail.com",
  },
];

export const boxRows = [
  {
    id: "a9df6055-7a4f-48af-b52d-33577089d13b",
    location: "Porto",
    active: true,
    password: "123456",
    status: "Picked-up"
  },
  {
    id: "3b621d38-fb63-4572-8ef7-eeedda8c3628",
    location: "Munich",
    active: false,
    password: "1234",
    status: "Delivered"
  },
  {
    id: "f8da0036-07d7-4de6-b761-4005c8d7828d",
    location: "Berlin",
    active: true,
    password: "12346",
    status: "Ordered"
  },
];

export const orderRows = [
  {
    id: 1,
    boxId: 2,
    customerId: 2,
    dispatcherId: 2,
    name: "Apple Airpods",
    img:
      "https://images.pexels.com/photos/7156886/pexels-photo-7156886.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
  {
    id: 2,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Watch Series 6",
    img:
      "https://www-konga-com-res.cloudinary.com/w_auto,f_auto,fl_lossy,dpr_auto,q_auto/media/catalog/order/G/I/174379_1610801608.jpg",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "canceled",
  },
  {
    id: 3,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Iphone 12",
    img:
      "https://www.reliancedigital.in/medias/Apple-12-Smartphones-491901537-i-1-1200Wx1200H-300Wx300H?context=bWFzdGVyfGltYWdlc3w0MjE5NnxpbWFnZS9qcGVnfGltYWdlcy9oZWEvaDExLzk0MDc3NDExMzI4MzAuanBnfDMyYzZkZjBlZmEyNTRkNGVjOGY4MTk1MWYwYzg2Yjg0YjdlNjI2N2QwNDgwMmM5OTUxZTcwYjZiYjA1YTc5Yzc",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "delivered",
  },
  {
    boxId: 1,
    id: 4,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Airpods",
    img:
      "https://images.pexels.com/photos/7156886/pexels-photo-7156886.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
  {
    id: 5,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Iphone 11Pro",
    img:
      "https://www.gizmochina.com/wp-content/uploads/2019/09/Apple-iPhone-11-Pro-500x500.jpg",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
  {
    id: 6,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Watch Series 7",
    img:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpAjx-8napBrslbug3yxYRh2yX0IBQI8hHxQ&usqp=CAU",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
  {
    id: 7,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Airpods",
    img:
      "https://images.pexels.com/photos/7156886/pexels-photo-7156886.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
  {
    id: 8,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Iphone 13",
    img:
      "https://m.media-amazon.com/images/I/71gm8v4uPBL._SL1500_.jpg",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "canceled",
  },
  {
    id: 9,
    name: "Apple Airpods",
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    img:
      "https://images.pexels.com/photos/7156886/pexels-photo-7156886.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
  {
    id: 10,
    boxId: 1,
    customerId: 1,
    dispatcherId: 1,
    name: "Apple Watch Series 7",
    img:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpAjx-8napBrslbug3yxYRh2yX0IBQI8hHxQ&usqp=CAU",
    customer: "Customer 1",
    dispatcher: "Dispatcher 1",
    status: "pending",
  },
];