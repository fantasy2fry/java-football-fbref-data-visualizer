
library(dplyr)
library(worldfootballR)
fb_league_urls(country = "ENG", gender = "M", season_end_year = 2021, tier = '2nd')
fb_team_urls("https://fbref.com/en/comps/10/2020-2021/2020-2021-Championship-Stats")
fb_player_urls("https://fbref.com/en/squads/fd962109/Fulham-Stats")  

big5_team_shooting <- fb_big5_advanced_season_stats(season_end_year= c(2019:2021), stat_type= "shooting", team_or_player= "team")
df=dplyr::glimpse(big5_team_shooting)
# function to extract Serie A match results data
serieA_2020 <- fb_match_results(country = "ITA", gender = "M", season_end_year = 2020, tier = "1st")
dplyr::glimpse(serieA_2020)

df=big5_team_shooting %>% filter(squa)