using System;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Q.WebAPI.Core;

namespace Q.WebAPI.Controllers {
    [Route("api/[controller]")]
    public class GetReportsController: Controller {
        // GET api/values
        // E.G. http://localhost:2533/api/GetReports?lastReportHash=asdasa
        [HttpGet]
        public async Task <ContentResult> Get (string lastReportHash){
            {
                if (lastReportHash == StaticData.LastReportHash.ToString())
                    return Content(JsonConvert.SerializeObject(StaticData.Reports.Where(report => report.DateTime.AddMinutes(10) > DateTime.Now)), "application/json");
                return Content(string.Empty);
            }
        }
    }
}