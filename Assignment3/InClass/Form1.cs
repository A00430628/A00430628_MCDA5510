using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Reflection;
using System.Data.Entity.Core.Objects;
using System.Data.Entity.Infrastructure;
using log4net;
using log4net.Config;

namespace InClass
{
    public partial class Form1 : Form
    {
        private static readonly ILog _log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
        public int index { get; private set; }
        public List<Customer> _customerList = new List<Customer>();

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)  // Form load Method
        {
            XmlConfigurator.Configure();
            using (Model1 _entity = new Model1())
            {
                _customerList = _entity.Customers.ToList();
                _log.Info("Start of the program");
                index = 0;
                validate();
                GetPrimaryKey();
                Display();   // calling Display Method for Bind the Customer Details in Form
            }

        }

        public void Display()   // Display Method is a common method to bind the Student details in datagridview after save,update and delete operation perform.
        {
            this.textBox1.Text = _customerList[index].FirstName;
            this.textBox2.Text = _customerList[index].LastName;
            this.textBox3.Text = _customerList[index].Street_No;
            this.textBox4.Text = _customerList[index].Street;
            this.textBox5.Text = _customerList[index].City;
            this.textBox6.Text = _customerList[index].Province;
            this.textBox7.Text = _customerList[index].Country;
            this.textBox8.Text = _customerList[index].Postal_Code;
            this.textBox9.Text = _customerList[index].Phone_No;
            this.textBox10.Text = _customerList[index].Email_Address;
        }

        public void resetForm() {
            this.textBox1.Text = "";
            this.textBox2.Text = "";
            this.textBox3.Text = "";
            this.textBox4.Text = "";
            this.textBox5.Text = "";
            this.textBox6.Text = "";
            this.textBox7.Text = "";
            this.textBox8.Text = "";
            this.textBox9.Text = "";
            this.textBox10.Text = "";
            this.textBox11.Text = "";
            this.textBox12.Text = "";
        }

        public void validate()
        {
            string emailPattern = @"^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$"; // Email address pattern  
            string postalCodePattern = @"^[A-Za-z]\d[A-Za-z][-]?\d[A-Za-z]\d$"; 
            string phonePattern = @"\(?\d{3}\)?-? *\d{3}-? *-?\d{4}"; // US Phone number pattern   
            foreach (System.Reflection.PropertyInfo prop in typeof(Customer).GetProperties())
            {
                Console.WriteLine("{0}", prop.GetType());
                // Create string variables that contain the patterns   
                String propValue = prop.GetValue(_customerList[index])?.ToString();
                if (propValue == null)
                {
                    propValue = "";
                }
                if (prop.Name == "Postal_Code")
                {
                    bool isPostalCodeValid = Regex.IsMatch(propValue != "" ? propValue : "", postalCodePattern);
                    if (!isPostalCodeValid)
                    {
                        // MessageBox.Show("Please enter a valid postal code");
                        String error = "Please enter a valid postal code";
                        this.textBox12.Text = error;
                        _log.Error("Please enter a valid postal code for " + this.textBox1.Text + " " + this.textBox2.Text);
                    }
                }
                else if (prop.Name == "Phone_No")
                {
                    bool isPhoneNoValid = Regex.IsMatch(propValue != "" ? propValue : "", phonePattern);
                    if (!isPhoneNoValid)
                    {
                        //MessageBox.Show("Please enter a valid phone number");
                        String error = "Please enter a valid phone number";
                        _log.Error("Please enter a valid phone number for " + this.textBox1.Text + " " + this.textBox2.Text);
                        this.textBox12.Text = error;
                    }
                }
                else if (prop.Name == "Email_Address")
                {
                    bool isEmailValid = Regex.IsMatch(propValue != "" ? propValue : "", emailPattern);
                    if (!isEmailValid)
                    {
                        //MessageBox.Show("Please enter a valid email");
                        String error = "Please enter a valid email";
                        _log.Error("Please enter a valid email for " + this.textBox1.Text + " " + this.textBox2.Text);
                        this.textBox12.Text = error;
                    }
                }
               
                   
                
            }
            if (this.textBox12.Text == "")
            {
                this.textBox12.Text = "All fields are valid";
                _log.Error("All fields are valid");
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (index > 0)
            {
                index--;
                resetForm();
                validate();
                GetPrimaryKey();
                Display();
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (index < _customerList.Count - 1)
            {
                index++;
                resetForm();
                validate();
                GetPrimaryKey();
                Display();
            }
        }


        public void GetPrimaryKey()
        {
            using (Model1 _entity = new Model1())
            {
                ObjectContext objectContext = ((IObjectContextAdapter)_entity).ObjectContext;
                ObjectSet<Customer> set = objectContext.CreateObjectSet<Customer>();
                IEnumerable<string> keyNames = set.EntitySet.ElementType
                                                            .KeyMembers
                                                            .Select(k => k.Name);

                Console.WriteLine("{0}", string.Join(",", keyNames.ToArray()));
                foreach (System.Reflection.PropertyInfo prop in typeof(Customer).GetProperties())
                {
                    String propValue = prop.GetValue(_customerList[index])?.ToString();
                    if (propValue == null)
                    {
                        propValue = "";
                    }
                    if (prop.Name == string.Join(",", keyNames.ToArray()))
                    {

                        this.textBox11.Text = propValue;
                        _log.Info("Current Primary Key is, "+propValue);

                    }

                }
            }
        }

    }
}
