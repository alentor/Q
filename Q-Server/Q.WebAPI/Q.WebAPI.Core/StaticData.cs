using System;
using System.Collections.Generic;
using Q.WebAPI.Core.DataStructures;

namespace Q.WebAPI.Core {
    public static class StaticData {
        public static readonly List <Report> Reports = new List <Report>();
        public static  Guid LastReportHash { get; set;}
    }
}