#Jack Cardwell
#Computer Science 1
#Professor Farid
#September 26, 2014

#import all of the necessary functions from cs1lib and random
from cs1lib import *

#define the constant height and width of the window
WINDOW_HEIGHT=400
WINDOW_WIDTH=400

#define the constant height and width of the rectangles
PADDLE_HEIGHT=80
PADDLE_WIDTH=20

#wall thickness for the horizontal walls   
WALL_THICKNESS=10

#coordinates for the text messages that appears on screen
TEXT_X=20
TEXT_Y=370

TEXT_X_2=20
TEXT_Y_2=380

#define how many pixels the paddle will move each time a key is pressed
MOVE=10

#define the MIN and MAX limit for the paddles, so that they cannot move off the screen
MIN=0
MAX=WINDOW_HEIGHT-80

#define the constant radius of the ball to 8 pixels
RADIUS=8

#define the constant sleep time between loops 
TIME=.02


#define a function to determine if the ball hits the right paddle
def hit_right_paddle(x_circle,y_circle,r_paddle_x,r_paddle_y):
    if x_circle+RADIUS>=r_paddle_x and y_circle<=r_paddle_y+PADDLE_HEIGHT and y_circle>=r_paddle_y:
        return "True"

#define a function to determine if the ball hits the left paddle
def hit_left_paddle(x_circle,y_circle,l_paddle_x,l_paddle_y,WIDTH):
    if x_circle-RADIUS<=l_paddle_x+PADDLE_WIDTH and y_circle<=l_paddle_y+PADDLE_HEIGHT and y_circle>=l_paddle_y:
        return "True"

#define a function to determine if the ball hits a horizontal wall
def hit_a_wall(y_circle, WINDOW_HEIGHT):
    if y_circle+RADIUS>=WINDOW_HEIGHT-WALL_THICKNESS or y_circle-RADIUS<=WALL_THICKNESS:
        return "True"

def out_of_bounds(x_circle,WINDOW_WIDTH):
    if x_circle+RADIUS>=WINDOW_WIDTH or x_circle-RADIUS<=0:
        return "True"

#define functions to move the paddle
def left_up(l_paddle_y,MIN):
    if is_key_pressed("a") and l_paddle_y>MIN+WALL_THICKNESS:
        return "True"

def left_down(l_paddle_y,MAX):
    if is_key_pressed("z") and l_paddle_y<MAX-WALL_THICKNESS:
        return "True"
    
def right_up(r_paddle_y,MIN):
    if is_key_pressed("k") and r_paddle_y>MIN+WALL_THICKNESS:
        return "True"
    
def right_down(r_paddle_y,MAX):
    if is_key_pressed("m") and r_paddle_y<MAX-WALL_THICKNESS:
        return "True"

#define functions to end the game or restart the game
def end_game():
    if is_key_pressed("q"):
        return "True"
    
def restart_game():
    if is_key_pressed(" "):
        return "True"

#define a function to reset the program to the initial conditions when starting a new game    
def new_game():
    
    #set the fill color of the paddles  
    set_fill_color(0,1,0) #green
    
    #define the coordinates of the two rectangular paddles
    l_paddle_x=0
    l_paddle_y=0
    
    r_paddle_x=380
    r_paddle_y=320
    
    #define the coordinates of the circle
    x_circle=WINDOW_WIDTH/2
    y_circle=WINDOW_HEIGHT/2
    
    #define the magnitude of the direction of the ball movement
    ball_move_x=3
    ball_move_y=3
    return l_paddle_x, l_paddle_y, r_paddle_x, r_paddle_y, x_circle, y_circle, ball_move_x, ball_move_y

#define a function to draw the horizontal walls
def draw_walls():
    set_fill_color(1,1,0)
    draw_rectangle(0,0,WINDOW_WIDTH,WALL_THICKNESS)
    draw_rectangle(0,390,WINDOW_WIDTH,WALL_THICKNESS)

#define a function to draw the text that appears on screen    
def quit_text():
    enable_stroke()
    set_stroke_color(1,1,1)
    draw_text("Press q at any time to quit", TEXT_X,TEXT_Y)
    disable_stroke()
    
def restart_text():
    enable_stroke()
    set_stroke_color(1,1,1)
    draw_text("Press spacebar after the current game ends to start again", TEXT_X_2,TEXT_Y_2)
    disable_stroke()
    
#this program will open up a window and allow the user to play a game of "pong" by using certain keys
def play_pong():
    
    #set a boolean to determine if the game is in progress or over
    GAME_OVER=False
    
    #clear the background of the new window 
    #set_clear_color(0,0,1) #blue
    
    set_clear_color(0,0,1)
    clear()
    
    #disable the stroke so there is no outline on the paddles and enable smoothing to make them appear better
    disable_stroke()
    enable_smoothing()
    
    #set the fill color of the paddles  
    set_fill_color(0,1,0) #green
    
    #define the coordinates of the two rectangular paddles
    l_paddle_x=0
    l_paddle_y=0
    
    r_paddle_x=380
    r_paddle_y=320
    
    #define the coordinates of the circle
    x_circle=WINDOW_WIDTH/2
    y_circle=WINDOW_HEIGHT/2
    
    #define the magnitude of the direction of the ball movement
    ball_move_x=3
    ball_move_y=3
    
    #the first one in the upper-left corner
    draw_rectangle(l_paddle_x,l_paddle_y,PADDLE_WIDTH,PADDLE_HEIGHT)
   
    #the second one in the bottom right
    draw_rectangle(r_paddle_x,r_paddle_y,PADDLE_WIDTH,PADDLE_HEIGHT)
    
    #draw the ball in the middle of the screen 
    set_fill_color(1,0,0) #red
    draw_circle(x_circle,y_circle,RADIUS)
    
    #draw the two walls that reflect the ball
    draw_walls()
    
    #define an while loop that will move the left paddle if they don't move out of the window
    while not window_closed():
        if GAME_OVER and restart_game():
            
            #assign values to the variables, based on the return in the function new_game...better than creating global variables
            l_paddle_x, l_paddle_y, r_paddle_x, r_paddle_y, x_circle, y_circle, ball_move_x, ball_move_y=new_game()  
            
            #reset GAME_OVER to false so that the loop can continue
            GAME_OVER= False    
                     
        elif not GAME_OVER:
            #use the functions to move the right and left paddles as defined by the conditions above
            if left_up(l_paddle_y,MIN):
                l_paddle_y=l_paddle_y-MOVE
            
            elif left_down(l_paddle_y,MAX):
                l_paddle_y=l_paddle_y+MOVE
        
            if right_up(r_paddle_y,MIN):
                r_paddle_y=r_paddle_y-MOVE
        
            elif right_down(r_paddle_y,MAX):
                r_paddle_y=r_paddle_y+MOVE
        
            #reverses the direction of the ball if it hits a wall or the paddles
            if hit_right_paddle(x_circle,y_circle,r_paddle_x,r_paddle_y):
                ball_move_x=-ball_move_x
                
                
            elif hit_left_paddle(x_circle,y_circle,l_paddle_x,l_paddle_y,PADDLE_WIDTH):
                ball_move_x=-ball_move_x
                
                
            elif hit_a_wall(y_circle,WINDOW_HEIGHT):
                ball_move_y=-ball_move_y
        
            elif out_of_bounds(x_circle,WINDOW_WIDTH):
                GAME_OVER=True
        
            #quitting the game
            if end_game():
                cs1_quit()
                
            #move the ball
            x_circle=x_circle+ball_move_x
            y_circle=y_circle+ball_move_y
            
            #clear the old drawings so that you are not layering paddles on top of each other   
            clear()
            
            #create text on the bottom of the screen
            quit_text()
            restart_text()
            
            #draw the ball
            set_fill_color(1,0,0)
            draw_circle(x_circle,y_circle,RADIUS)
            
            #draw the two new rectangles    
            set_fill_color(0,1,0) #green
        
            draw_rectangle(l_paddle_x,l_paddle_y,PADDLE_WIDTH,PADDLE_HEIGHT)
            draw_rectangle(r_paddle_x,r_paddle_y,PADDLE_WIDTH,PADDLE_HEIGHT)
            
            #draw the walls
            draw_walls()
            
            #transfer the drawing from the buffer layer to the console and sleep the program so that the user can catch up
            request_redraw()
            sleep(TIME)
            
start_graphics(play_pong,"Pong",WINDOW_WIDTH,WINDOW_HEIGHT)

    
    
    
    