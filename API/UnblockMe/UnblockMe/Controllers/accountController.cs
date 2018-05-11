using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace UnblockMe.Controllers
{
    public class accountController : ApiController
    {
        [HttpGet]
        public List<account> GetAccLists()
        {
           DBUnblockMeDataContext db = new DBUnblockMeDataContext();
            return db.accounts.ToList();
        }
///
 

[HttpGet]
        public account GetAcc(String username)
        {
            DBUnblockMeDataContext db = new DBUnblockMeDataContext();
            return db.accounts.FirstOrDefault(x => x.username == username);
        }
///
 

[HttpPost]
        public bool InsertNewAcc(string username, string password)
        {
            try
            {
                DBUnblockMeDataContext db = new DBUnblockMeDataContext();
                account acc = new account();
                acc.username = username;
                acc.password = password;
                acc.level = 1;
                db.accounts.InsertOnSubmit(acc);
                db.SubmitChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }
///
 

[HttpPut]
        public bool UpdatePass( string name, string pass)
        {
            try
            {
                DBUnblockMeDataContext db = new DBUnblockMeDataContext();
                //lấy food tồn tại ra
                account acc= db.accounts.FirstOrDefault(x => x.username == name);
                if (acc== null) return false;//không tồn tại false
                acc.username = name;
                acc.password = pass;
                db.SubmitChanges();//xác nhận chỉnh sửa
                return true;
            }
            catch
            {
                return false;
            }
        }
        ///
        [HttpPut]
        public bool UpdateLevel(string name, int level)
        {
            try
            {
                DBUnblockMeDataContext db = new DBUnblockMeDataContext();
                //lấy food tồn tại ra
                account acc = db.accounts.FirstOrDefault(x => x.username == name);
                if (acc == null) return false;//không tồn tại false
                acc.username = name;
                acc.level = level;
                db.SubmitChanges();//xác nhận chỉnh sửa
                return true;
            }
            catch
            {
                return false;
            }
        }

        [HttpDelete]
        public bool DeleteFood(String name)
        {
            DBUnblockMeDataContext db = new DBUnblockMeDataContext();
            //lấy food tồn tại ra
            account acc = db.accounts.FirstOrDefault(x => x.username == name);
            if (acc == null) return false;
            db.accounts.DeleteOnSubmit(acc);
            db.SubmitChanges();
            return true;
        }
    }
}
