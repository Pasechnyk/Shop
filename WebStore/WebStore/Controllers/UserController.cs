using Microsoft.AspNetCore.Mvc;
using WebStore.Data;
using WebStore.Data.Entities;
using WebStore.Models.Users;
using System.Linq;
using Microsoft.AspNetCore.Identity;
using WebStore.Data.Entities.Identity;
using WebStore.Interfaces;
using System;
using WebStore.Services;

namespace WebStore.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly UserManager<UserEntity> _userManager;
        private readonly IJwtTokenService _jwtTokenService;

        public UserController(UserManager<UserEntity> userManager, IJwtTokenService jwtTokenService)
        {
            _userManager = userManager;
            _jwtTokenService = jwtTokenService;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] UserLoginViewModel model)
        {
            var user = await _userManager.FindByEmailAsync(model.Email);
            if (user == null)
            {
                return BadRequest();
            }
            var isAuth = await _userManager.CheckPasswordAsync(user, model.Password);
            if (!isAuth)
            {
                return BadRequest();
            }
            var token = await _jwtTokenService.CreateToken(user);
            return Ok(new { token });
        }

        //[HttpPost("register")]
        //public IActionResult Register([FromBody] UserCreateModel model)
        //{
        //    if (_appContext.Users.Any(u => u.Username == model.Username))
        //    {
        //        return BadRequest("Username already exists.");
        //    }

        //    if (_appContext.Users.Any(u => u.Email == model.Email))
        //    {
        //        return BadRequest("Email already exists.");
        //    }

        //    var user = new UserEntity
        //    {
        //        Username = model.Username,
        //        Password = model.Password,
        //        Email = model.Email

        //    };

        //    _appContext.Users.Add(user);
        //    _appContext.SaveChanges();

        //    return Ok(new { Message = "User registered successfully", UserId = user.Id });
        //}
    }
}

