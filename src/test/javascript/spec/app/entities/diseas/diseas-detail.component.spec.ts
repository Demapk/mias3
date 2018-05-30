/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { DiseasDetailComponent } from '../../../../../../main/webapp/app/entities/diseas/diseas-detail.component';
import { DiseasService } from '../../../../../../main/webapp/app/entities/diseas/diseas.service';
import { Diseas } from '../../../../../../main/webapp/app/entities/diseas/diseas.model';

describe('Component Tests', () => {

    describe('Diseas Management Detail Component', () => {
        let comp: DiseasDetailComponent;
        let fixture: ComponentFixture<DiseasDetailComponent>;
        let service: DiseasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [DiseasDetailComponent],
                providers: [
                    DiseasService
                ]
            })
            .overrideTemplate(DiseasDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiseasDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiseasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Diseas(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.diseas).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
