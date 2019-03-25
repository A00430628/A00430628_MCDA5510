namespace InClass
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class Model1 : DbContext
    {
        public Model1() : base("name=Model11"){
        }

        public virtual DbSet<Customer> Customers { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Customer>()
                .Property(e => e.FirstName)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.LastName)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Street_No)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Street)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.City)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Province)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Country)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Postal_Code)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Phone_No)
                .IsUnicode(false);

            modelBuilder.Entity<Customer>()
                .Property(e => e.Email_Address)
                .IsUnicode(false);
        }
    }
}
