package lk.ijse.customer.dto;
    public class CustomerDto {
        private String id;
        private String name;
        private String address;
        private String tele;

        public CustomerDto() {
        }

        public CustomerDto(String id, String name, String address, String tele) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.tele = tele;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        @Override
        public String toString() {
            return "CustomerDto{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", tele='" + tele + '\'' +
                    '}';
        }
    }
