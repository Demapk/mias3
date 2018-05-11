/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { SpecialityDetailComponent } from '../../../../../../main/webapp/app/entities/speciality/speciality-detail.component';
import { SpecialityService } from '../../../../../../main/webapp/app/entities/speciality/speciality.service';
import { Speciality } from '../../../../../../main/webapp/app/entities/speciality/speciality.model';

describe('Component Tests', () => {

    describe('Speciality Management Detail Component', () => {
        let comp: SpecialityDetailComponent;
        let fixture: ComponentFixture<SpecialityDetailComponent>;
        let service: SpecialityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [SpecialityDetailComponent],
                providers: [
                    SpecialityService
                ]
            })
            .overrideTemplate(SpecialityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpecialityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Speciality(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.speciality).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
